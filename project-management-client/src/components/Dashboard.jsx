import { Component } from "react";
import { connect } from "react-redux";
import { getProjects } from "../services/project-service";
import { projectsLoaded } from "../store/entities";
import CreateProjectButton from "./CreateButton";
import ProjectItem from "./ProjectItem";

class Dashboard extends Component {
  async componentDidMount() {
    const { projectsLoaded } = this.props;
    const { data: projects } = await getProjects();
    projectsLoaded(projects);
  }

  render() {
    const { projects } = this.props;
    return (
      <div className="projects">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">Projects</h1>
              <CreateProjectButton />
              {projects.map((project) => {
                const { id, projectIdentifier, projectName, description } =
                  project;
                return (
                  <ProjectItem
                    key={id}
                    projectIdentifer={projectIdentifier}
                    projectName={projectName}
                    projectDescription={description}
                  />
                );
              })}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  const { projects, errors } = state.entities;
  return {
    projects,
    errors,
  };
};

const mapDispatchToProps = (dispatch) => ({
  projectsLoaded: (data) => dispatch(projectsLoaded({ data })),
});

export default connect(mapStateToProps, mapDispatchToProps)(Dashboard);
