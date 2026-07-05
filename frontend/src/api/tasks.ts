import { apiClient } from "./client";
import type {
  PagedResponse,
  Task,
  TaskRequest,
  TaskStatus,
} from "../types/task";

export async function getTasks(
  status?: TaskStatus | null,
  page = 0,
  size = 10,
  sort = "createdAt,desc",
): Promise<PagedResponse<Task>> {
  const params: Record<string, string | number> = { page, size, sort };
  if (status) params.status = status;
  const res = await apiClient.get<PagedResponse<Task>>("/api/tasks", {
    params,
  });
  return res.data;
}

export async function getTaskById(id: string): Promise<Task> {
  const res = await apiClient.get<Task>(`/api/tasks/${id}`);
  return res.data;
}

export async function createTask(data: TaskRequest): Promise<Task> {
  const res = await apiClient.post<Task>("/api/tasks", data);
  return res.data;
}

export async function updateTask(id: string, data: TaskRequest): Promise<Task> {
  const res = await apiClient.put<Task>(`/api/tasks/${id}`, data);
  return res.data;
}

export async function deleteTask(id: string): Promise<void> {
  await apiClient.delete(`/api/tasks/${id}`);
}
