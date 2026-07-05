import { useState } from 'react'
import { useQuery } from '@tanstack/react-query'
import { getTasks } from './api/tasks'
import type { Task, TaskStatus } from './types/task'
import StatusFilter from './components/StatusFilter'
import TaskList from './components/TaskList'
import Pagination from './components/Pagination'
import TaskForm from './components/TaskForm'
import ConfirmDialog from './components/ConfirmDialog'

type ModalState = { type: 'create' } | { type: 'edit'; task: Task } | null

export default function App() {
  const [status, setStatus] = useState<TaskStatus | null>(null)
  const [page, setPage] = useState(0)
  const [modal, setModal] = useState<ModalState>(null)
  const [deleteTarget, setDeleteTarget] = useState<Task | null>(null)

  const { data, isLoading, isError, refetch } = useQuery({
    queryKey: ['tasks', { status, page, size: 10, sort: 'createdAt,desc' }],
    queryFn: () => getTasks(status, page, 10, 'createdAt,desc'),
  })

  const handleStatusChange = (s: TaskStatus | null) => {
    setStatus(s)
    setPage(0)
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <header className="bg-white shadow-sm border-b">
        <div className="max-w-3xl mx-auto px-4 py-6">
          <h1 className="text-2xl font-bold text-gray-900">Todo App</h1>
        </div>
      </header>
      <main className="max-w-3xl mx-auto px-4 py-6 space-y-4">
        <div className="flex items-center justify-between">
          <StatusFilter value={status} onChange={handleStatusChange} />
          <button
            onClick={() => setModal({ type: 'create' })}
            className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 cursor-pointer"
          >
            + New Task
          </button>
        </div>
        <TaskList
          tasks={data?.content ?? []}
          totalElements={data?.totalElements ?? 0}
          isLoading={isLoading}
          isError={isError}
          onRetry={() => refetch()}
          onEdit={(task) => setModal({ type: 'edit', task })}
          onDelete={(task) => setDeleteTarget(task)}
        />
        {data && data.totalPages > 1 && (
          <Pagination
            page={page}
            totalPages={data.totalPages}
            onChange={setPage}
          />
        )}
      </main>
      {modal?.type === 'create' && (
        <TaskForm onClose={() => setModal(null)} />
      )}
      {modal?.type === 'edit' && (
        <TaskForm task={modal.task} onClose={() => setModal(null)} />
      )}
      {deleteTarget && (
        <ConfirmDialog
          task={deleteTarget}
          onClose={() => setDeleteTarget(null)}
        />
      )}
    </div>
  )
}