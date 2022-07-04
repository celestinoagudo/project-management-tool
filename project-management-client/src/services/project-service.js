import config from "../config.json";
import http from "./http-service";

export function saveProject(project) {
  return http.post(`${config.projectEndPoint}`, project);
}

export function deleteProject(projectIdentifier) {
  return http.delete(`${config.projectEndPoint}/${projectIdentifier}`);
}

export function getProject(projectIdentifier) {
  return http.get(`${config.projectEndPoint}/${projectIdentifier}`);
}

export function getProjects() {
  return http.get(config.projectEndPoint);
}
