import { createSlice } from "@reduxjs/toolkit";
import { toast } from "react-toastify";
import { createSelector } from "reselect";
import projectInfoFormConstants from "../components/component-constants/project-info-form.json";

const slice = createSlice({
  name: "entities",
  initialState: {
    selectedProject: projectInfoFormConstants.emptyProject,
    projects: [],
    errors: [],
  },
  reducers: {
    projectSaved: (state, action) => {
      const { data: response } = action.payload;
      const { data: projectToSave } = response;
      let { projects } = state;
      const filtered = state.projects.filter(
        (project) => project.id !== projectToSave.id
      );

      projects = filtered;
      projects.push(projectToSave);
    },
    errorAdded: (state, action) => {
      const { data: response } = action.payload;
      const { message } = response;
      const errorData = message.split("|");
      const { errors } = state;

      errors.push(errorData);
      for (const errorMessage of errorData) {
        toast.error(errorMessage, {
          position: toast.POSITION.TOP_RIGHT,
        });
      }
    },
    projectsLoaded: (state, action) => {
      const { data: response } = action.payload;
      state.projects = response.data;
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
