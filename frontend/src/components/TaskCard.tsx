import type { Task } from "../types/task";

export default function TaskCard({
  task,
  onEdit,
  onDelete,
}: {
  task: Task;
  onEdit: () => void;
  onDelete: () => void;
}) {
  return (
    <div className="bg-white rounded-lg shadow-sm border p-4 flex items-start justify-between gap-4">
      <div className="flex-1 min-w-0">
        <div className="flex items-center gap-2 mb-1">
          <span
            className={`text-xs font-medium px-2 py-0.5 rounded-full ${
              task.status === "COMPLETED"
                ? "bg-green-100 text-green-700"
                : "bg-yellow-100 text-yellow-700"
            }`}
          >
            {task.status}
          </span>
          <h3 className="font-semibold text-gray-900 truncate">{task.title}</h3>
        </div>
        {task.description && (
          <p className="text-sm text-gray-500 mb-2">{task.description}</p>
        )}
        <p className="text-xs text-gray-400">
          Created: {new Date(task.createdAt).toLocaleString()}
        </p>
      </div>
      <div className="flex gap-2 shrink-0">
        <button
          onClick={onEdit}
          className="text-sm text-blue-600 hover:underline cursor-pointer"
        >
          Edit
        </button>
        <button
          onClick={onDelete}
          className="text-sm text-red-600 hover:underline cursor-pointer"
        >
          Delete
        </button>
      </div>
    </div>
  );
}
