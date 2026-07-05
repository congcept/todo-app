import { useMutation, useQueryClient } from "@tanstack/react-query";
import { toggleTaskStatus } from "../api/tasks";
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
  const queryClient = useQueryClient();

  const toggleMutation = useMutation({
    mutationFn: () => toggleTaskStatus(task.id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["tasks"] });
    },
  });

  return (
    <div className="bg-white shadow-sm border p-4 flex items-start justify-between gap-4">
      <div className="flex items-center gap-3 flex-1 min-w-0">
        <input
          type="checkbox"
          checked={task.status === "COMPLETED"}
          onChange={() => toggleMutation.mutate()}
          className="mt-0.5 cursor-pointer shrink-0"
        />
        <div className="flex-1 min-w-0">
          <div className="flex items-center gap-2 mb-1">
            <span
              className={`text-xs font-medium px-2 py-0.5 ${
                task.status === "COMPLETED"
                  ? "bg-green-100 text-green-700"
                  : "bg-yellow-100 text-yellow-700"
              }`}
            >
              {task.status}
            </span>
            <h3
              className={`font-semibold truncate ${
                task.status === "COMPLETED"
                  ? "text-gray-400 line-through"
                  : "text-gray-900"
              }`}
            >
              {task.title}
            </h3>
          </div>
          {task.description && (
            <p className="text-sm text-gray-500 mb-2">{task.description}</p>
          )}
          <p className="text-xs text-gray-400">
            Created: {new Date(task.createdAt).toLocaleString()}
          </p>
        </div>
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
