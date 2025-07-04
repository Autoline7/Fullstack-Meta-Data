import { clsx, type ClassValue } from "clsx"
import { twMerge } from "tailwind-merge"

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}


/* Api */
import axios from "axios";

// Axios Instance
export const api = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080',
});

// fetching functions
export const fetcher = (url : string) => api.get(url).then(res => res.data);


export const uploader = (url: string, formData: FormData) =>
  api.post(url, formData).then(res => res.data);

