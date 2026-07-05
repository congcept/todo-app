import type { Task } from "../types/task";
import TaskCard from "./TaskCard";

export default function TaskList({
  tasks,
  totalElements,
  isLoading,
  isError,
  onRetry,
  onEdit,
  onDelete,
}: {
  tasks: Task[];
  totalElements: number;
  isLoading: boolean;
  isError: boolean;
  onRetry: () => void;
  onEdit: (task: Task) => void;
  onDelete: (task: Task) => void;
}) {
  if (isLoading) {
    return <p className="text-center text-gray-500 py-8">Loading tasks...</p>;
  }
  if (isError) {
    return (
      <div className="text-center py-8">
        <p className="text-red-600 mb-2">Failed to load tasks</p>
        <button
          onClick={onRetry}
          className="text-blue-600 hover:underline cursor-pointer"
        >
          Retry
        </button>
      </div>
    );
  }
  if (totalElements === 0) {
    return (
      <p className="text-center text-gray-400 py-8">
        No tasks yet. Create your first one!
      </p>
    );
  }
  return (
    <div className="space-y-3">
      {tasks.map((task) => (
        <TaskCard
          key={task.id}
          task={task}
          onEdit={() => onEdit(task)}
          onDelete={() => onDelete(task)}
        />
      ))}
    </div>
  );
}
