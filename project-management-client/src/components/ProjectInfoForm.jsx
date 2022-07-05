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
  projectAdded,
  updateSelectedProjectOnChange,
  updateSelectedProjectOnSubmit,
} from "../store/entities";
import TextArea from "./TextArea";
import TextField from "./TextField";

class ProjectInfoForm extends Component {
  constructor() {
    super();
    this.emptyProject = {
      projectName: "",
      projectIdentifier: "",
      description: "",
      startDate: "",
      endDate: "",
      id: "",
    };
    this.placeHolders = {
      namePlaceHolder: "Project Name",
      projectIdPlaceHolder: "Unique Project ID",
      descriptionPlaceHolder: "Project Description",
    };
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentDidMount() {
    const { selectedProject } = this.props;
    this.setState(selectedProject);
  }

  onChange(event) {
    const { updateSelectedProjectOnChange } = this.props;
    updateSelectedProjectOnChange(event.target);
  }

  async onSubmit(event) {
    event.preventDefault();
    const {
      projectAdded,
      errorAdded,
      errorsCleared,
      navigate,
      selectedProject,
      updateSelectedProjectOnSubmit,
    } = this.props;

    try {
      const { data: createdProject } = await saveProject(selectedProject);
      errorsCleared();
      navigate(config.dashboard);
      projectAdded(createdProject);
      updateSelectedProjectOnSubmit(this.emptyProject);
    } catch (error) {
      errorAdded(error.response.data);
    }
  }

  render() {
    const { onChange, onSubmit, placeHolders } = this;
    const { namePlaceHolder, projectIdPlaceHolder, descriptionPlaceHolder } =
      placeHolders;
    const { selectedProject } = this.props;
    console.log("selected project", selectedProject);
    const { projectName, projectIdentifier, description, startDate, endDate } =
      selectedProject;

    return (
      <div className="project">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Create Project</h5>
              <hr />
              <form onSubmit={onSubmit}>
                <TextField
                  type="text"
                  placeHolder={namePlaceHolder}
                  name="projectName"
                  controlledBy={projectName}
                  valueChangedHandler={onChange}
                />
                <TextField
                  type="text"
                  placeHolder={projectIdPlaceHolder}
                  name="projectIdentifier"
                  controlledBy={projectIdentifier}
                  valueChangedHandler={onChange}
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
                />
                <h6>Estimated End Date</h6>
                <TextField
                  type="date"
                  name="endDate"
                  controlledBy={endDate}
                  valueChangedHandler={onChange}
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
  projectAdded: (data) => dispatch(projectAdded({ data })),
  errorAdded: (data) => dispatch(errorAdded({ data })),
  errorsCleared: (data) => dispatch(errorsCleared({ data })),
  updateSelectedProjectOnChange: (data) =>
    dispatch(updateSelectedProjectOnChange({ data })),
  updateSelectedProjectOnSubmit: (data) =>
    dispatch(updateSelectedProjectOnSubmit({ data })),
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(getComponentWithParamsAndNavigate(ProjectInfoForm));
