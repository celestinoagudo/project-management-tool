import "bootstrap/dist/css/bootstrap.min.css";
import { Provider } from "react-redux";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Header";
import ProjectInfoForm from "./components/ProjectInfoForm";
import config from "./config.json";
import createStore from "./store/store";

function App() {
  const { projectInfo, dashboard } = config;
  return (
    <Provider store={createStore()}>
      <Router>
        <div className="App">
          <Header />
          <Routes>
            <Route exact path={projectInfo} element={<ProjectInfoForm />} />
            <Route exact path={dashboard} element={<Dashboard />} />
          </Routes>
        </div>
      </Router>
    </Provider>
  );
}

export default App;
