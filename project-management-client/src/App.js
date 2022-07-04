import "bootstrap/dist/css/bootstrap.min.css";
import { Provider } from "react-redux";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";
import AddProject from "./components/AddProject";
import Dashboard from "./components/Dashboard";
import Header from "./components/Header";
import config from "./config.json";
import createStore from "./store/store";

function App() {
  const { addProject, dashboard } = config;
  return (
    <Provider store={createStore()}>
      <Router>
        <div className="App">
          <Header />
          <Routes>
            <Route exact path={addProject} element={<AddProject />} />
            <Route exact path={dashboard} element={<Dashboard />} />
          </Routes>
        </div>
      </Router>
    </Provider>
  );
}

export default App;
