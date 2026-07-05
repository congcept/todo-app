import type { TaskStatus } from "../types/task";

const options: { label: string; value: TaskStatus | null }[] = [
  { label: "All", value: null },
  { label: "Pending", value: "PENDING" },
  { label: "Completed", value: "COMPLETED" },
];

export default function StatusFilter({
  value,
  onChange,
}: {
  value: TaskStatus | null;
  onChange: (v: TaskStatus | null) => void;
}) {
  return (
    <div className="flex gap-2">
      {options.map((opt) => (
        <button
          key={opt.label}
          onClick={() => onChange(opt.value)}
          className={`px-4 py-2 border cursor-pointer ${
            value === opt.value
              ? "bg-blue-600 text-white border-blue-600"
              : "bg-white text-gray-700 border-gray-300 hover:bg-gray-50"
          }`}
        >
          {opt.label}
        </button>
      ))}
    </div>
  );
}
