import axios from "axios";

const http = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
});
console.log("BASE URL (VITE_API_BASE_URL):", import.meta.env.VITE_API_BASE_URL);

http.interceptors.request.use((config) => {
    console.log("REQ =>", config.baseURL, config.url);
  const token = localStorage.getItem("token");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

export default http;
