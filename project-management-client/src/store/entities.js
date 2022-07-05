import { createSlice } from "@reduxjs/toolkit";
import { toast } from "react-toastify";

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
    projectAdded: (state, action) => {
      state.projects.push(action.payload.data);
    },
    errorAdded: (state, action) => {
      const errorData = action.payload.data;
      state.errors.push(errorData);
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
    updateSelectedProjectOnSubmit: (state, action) => {
      state.selectedProject = action.payload.data;
    },
    errorsCleared: (state, action) => {
      state.errors = [];
    },
  },
});

export const {
  projectAdded,
  errorAdded,
  projectsLoaded,
  projectRemoved,
  errorsCleared,
  updateSelectedProjectOnChange,
  updateSelectedProjectOnSubmit,
} = slice.actions;

export default slice.reducer;
