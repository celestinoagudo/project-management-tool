import { Link } from "react-router-dom";
import config from "../config.json";
const CreateProjectButton = () => {
  return (
    <Link to={config.addProject} className="btn btn-lg btn-info mb-3">
      Create a Project
    </Link>
  );
};

export default CreateProjectButton;
