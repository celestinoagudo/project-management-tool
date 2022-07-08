import { faEdit, faFlagCheckered } from "@fortawesome/free-solid-svg-icons";
import { Component } from "react";
import { connect } from "react-redux";
import { toast, ToastContainer } from "react-toastify";
import config from "../config.json";
import { getComponentWithParamsAndNavigate } from "../services/get-params-navigate";
import { deleteProject } from "../services/project-service";
import {
  getProjectByIdentifier,
  projectRemoved,
  updateSelectedProject,
} from "../store/entities";
import Action from "./Action";

class ProjectItem extends Component {
  handleDelete = async (projectIdentifier) => {
    const { projectRemoved } = this.props;
    try {
      projectRemoved(projectIdentifier);
      await deleteProject(projectIdentifier);
    } catch (error) {
      if (
        error.response &&
        error.response.status >= 400 &&
        error.response.status < 500
      ) {
        toast.error(error.response.data, {
          position: toast.POSITION.TOP_RIGHT,
        });
      }
    }
  };

  handleUpdate = (projectIdentifier) => {
    const { getProjectByIdentifier, updateSelectedProject, navigate } =
      this.props;
    const selectedProjects = getProjectByIdentifier(projectIdentifier);
    const project = selectedProjects.length && selectedProjects[0];
    updateSelectedProject(project);
    navigate(config.projectInfo);
  };

  render() {
    const { projectIdentifer, projectName, projectDescription } = this.props;
    const { handleDelete, handleUpdate } = this;

    return (
      <div className="container">
        <div className="card card-body bg-light mb-3">
          <div className="row">
            <div className="col-2">
              <span className="mx-auto">{projectIdentifer}</span>
            </div>
            <div className="col-lg-6 col-md-4 col-8">
              <h3>{projectName}</h3>
              <p>{projectDescription}</p>
            </div>
            <div className="col-md-4 d-none d-lg-block">
              <ul className="list-group">
                <Action
                  redirectTo="/"
                  actionIcon={faFlagCheckered}
                  displayText="Project Board"
                  customClasses="board text-primary"
                />
                <Action
                  actionIcon={faEdit}
                  displayText="Update Project"
                  customClasses="update text-success"
                  onClick={() => handleUpdate(projectIdentifer)}
                />
                <Action
                  redirectTo="#"
                  actionIcon={faFlagCheckered}
                  displayText="Delete Project"
                  customClasses="delete text-danger"
                  onClick={() => handleDelete(projectIdentifer)}
                />
              </ul>
            </div>
          </div>
        </div>
        <ToastContainer autoClose={2000} />
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  const { projects, errors } = state.entities;
  return {
    projects,
    errors,
    getProjectByIdentifier: (projectIdentifier) =>
      getProjectByIdentifier(projectIdentifier)(state),
  };
};

const mapDispatchToProps = (dispatch) => ({
  projectRemoved: (data) => dispatch(projectRemoved({ data })),
  updateSelectedProject: (data) => dispatch(updateSelectedProject({ data })),
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(getComponentWithParamsAndNavigate(ProjectItem));
