import { createSlice } from "@reduxjs/toolkit";
import { toast } from "react-toastify";
import { createSelector } from "reselect";

const slice = createSlice({
  name: "entities",
  initialState: {
    selectedProject: {
      projectName: "",
      projectIdentifier: "",
      description: "",
      startDate: "",
      endDate: "",
      id: "",
    },
    projects: [],
    errors: [],
  },
  reducers: {
    projectSaved: (state, action) => {
      const { data: projectToSave } = action.payload;
      let { projects } = state;
      const filtered = state.projects.filter(
        (project) => project.id !== projectToSave.id
      );
      projects = filtered;
      projects.push(projectToSave);
    },
    errorAdded: (state, action) => {
      const { data: errorData } = action.payload;
      const { errors } = state;
      errors.push(errorData);
      for (const errorMessage of Object.values(errorData)) {
        toast.error(errorMessage, {
          position: toast.POSITION.TOP_RIGHT,
        });
      }
    },
    projectsLoaded: (state, action) => {
      state.projects = action.payload.data;
    },
    projectRemoved: (state, action) => {
      const filtered = state.projects.filter(
        (project) => project.projectIdentifier !== action.payload.data
      );
      state.projects = filtered;
    },
    updateSelectedProjectOnChange: (state, action) => {
      const { value, name } = action.payload.data;
      state.selectedProject[name] = value;
    },
    updateSelectedProject: (state, action) => {
      state.selectedProject = action.payload.data;
    },
    errorsCleared: (state, action) => {
      state.errors = [];
    },
  },
});

export const getProjectByIdentifier = (projectIdentifier) =>
  createSelector(
    (state) => state.entities.projects,
    (projects) =>
      projects.filter((project) =>
        project.projectIdentifier
          .toLowerCase()
          .includes(projectIdentifier.toLowerCase())
      )
  );

export const {
  projectSaved,
  errorAdded,
  projectsLoaded,
  projectRemoved,
  errorsCleared,
  updateSelectedProjectOnChange,
  updateSelectedProject,
} = slice.actions;

export default slice.reducer;
