import { createSlice } from "@reduxjs/toolkit";
import { toast } from "react-toastify";

const slice = createSlice({
  name: "entities",
  initialState: {
    projects: [],
    errors: [],
  },
  reducers: {
    projectAdded: (state, action) => {
      state.projects.push(action.payload.data);
      // toast.success("Project Successfully Added !", {
      //   position: toast.POSITION.TOP_LEFT,
      // });
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
        (project) =>
          project.projectIdentifier !== action.payload.data.projectIdentifier
      );
      state.projects = filtered;
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
} = slice.actions;

export default slice.reducer;
