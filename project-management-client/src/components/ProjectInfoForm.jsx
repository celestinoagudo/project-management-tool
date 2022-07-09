import { Component } from "react";
import { connect } from "react-redux";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import config from "../config.json";
import { getComponentWithParamsAndNavigate } from "../services/get-params-navigate";
import { saveProject } from "../services/project-service";
import {
  errorAdded,
  errorsCleared,
  projectSaved,
  updateSelectedProject,
  updateSelectedProjectOnChange,
} from "../store/entities";
import formConstants from "./component-constants/project-info-form.json";
import TextArea from "./TextArea";
import TextField from "./TextField";

class ProjectInfoForm extends Component {
  emptyProject = formConstants.emptyProject;
  placeHolders = formConstants.placeHolders;

  componentDidMount() {
    const { selectedProject } = this.props;
    this.setState(selectedProject);
  }

  onChange = (event) => {
    const { updateSelectedProjectOnChange } = this.props;
    updateSelectedProjectOnChange(event.target);
  };

  onSubmit = async (event) => {
    event.preventDefault();
    const {
      projectSaved,
      errorAdded,
      errorsCleared,
      navigate,
      selectedProject,
      updateSelectedProject,
    } = this.props;

    try {
      const { data: savedProject } = await saveProject(selectedProject);
      errorsCleared();
      navigate(config.dashboard);
      projectSaved(savedProject);
      updateSelectedProject(this.emptyProject);
    } catch (error) {
      errorAdded(error.response.data);
    }
  };

  getFormTitle(id) {
    return +id > 0 ? (
      <h5 className="display-4 text-center">Update Project</h5>
    ) : (
      <h5 className="display-4 text-center">Create Project</h5>
    );
  }

  render() {
    const { onChange, onSubmit, placeHolders, getFormTitle } = this;
    const { namePlaceHolder, projectIdPlaceHolder, descriptionPlaceHolder } =
      placeHolders;
    const { selectedProject } = this.props;

    const {
      id,
      projectName,
      projectIdentifier,
      description,
      startDate,
      endDate,
    } = selectedProject;

    return (
      <div className="project">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              {getFormTitle(id)}
              <hr />
              <form onSubmit={onSubmit}>
                <TextField
                  type="text"
                  placeHolder={namePlaceHolder}
                  name="projectName"
                  controlledBy={projectName}
                  valueChangedHandler={onChange}
                  isDisabled={false}
                />
                <TextField
                  type="text"
                  placeHolder={projectIdPlaceHolder}
                  name="projectIdentifier"
                  controlledBy={projectIdentifier}
                  valueChangedHandler={onChange}
                  isDisabled={+id > 0}
                />
                <TextArea
                  placeHolder={descriptionPlaceHolder}
                  name="description"
                  controlledBy={description}
                  valueChangedHandler={onChange}
                />
                <h6>Start Date</h6>
                <TextField
                  type="date"
                  name="startDate"
                  controlledBy={startDate}
                  valueChangedHandler={onChange}
                  isDisabled={false}
                />
                <h6>Estimated End Date</h6>
                <TextField
                  type="date"
                  name="endDate"
                  controlledBy={endDate}
                  valueChangedHandler={onChange}
                  isDisabled={false}
                />
                <input
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                />
              </form>
            </div>
          </div>
        </div>
        <ToastContainer autoClose={2000} />
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  const { projects, errors, selectedProject } = state.entities;
  return {
    projects,
    errors,
    selectedProject,
  };
};

const mapDispatchToProps = (dispatch) => ({
  projectSaved: (data) => dispatch(projectSaved({ data })),
  errorAdded: (data) => dispatch(errorAdded({ data })),
  errorsCleared: (data) => dispatch(errorsCleared({ data })),
  updateSelectedProjectOnChange: (data) =>
    dispatch(updateSelectedProjectOnChange({ data })),
  updateSelectedProject: (data) => dispatch(updateSelectedProject({ data })),
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(getComponentWithParamsAndNavigate(ProjectInfoForm));
