import { configureStore } from "@reduxjs/toolkit";
import reducer from "./root";

export default function createStore() {
  return configureStore({
    reducer,
    middleware: (getDefaultMiddleware) =>
      getDefaultMiddleware({
        serializableCheck: false,
      }),
  });
}
