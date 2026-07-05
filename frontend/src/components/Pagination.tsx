export default function Pagination({
  page,
  totalPages,
  onChange,
}: {
  page: number
  totalPages: number
  onChange: (p: number) => void
}) {
  return (
    <div className="flex items-center justify-center gap-4 py-4">
      <button
        onClick={() => onChange(page - 1)}
        disabled={page === 0}
        className="px-3 py-1 border cursor-pointer disabled:opacity-40 disabled:cursor-not-allowed"
      >
        &lt;
      </button>
      <span className="text-sm text-gray-600">
        Page {page + 1} of {totalPages}
      </span>
      <button
        onClick={() => onChange(page + 1)}
        disabled={page >= totalPages - 1}
        className="px-3 py-1 border cursor-pointer disabled:opacity-40 disabled:cursor-not-allowed"
      >
        &gt;
      </button>
    </div>
  )
}