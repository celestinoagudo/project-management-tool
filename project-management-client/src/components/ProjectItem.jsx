import { faEdit, faFlagCheckered } from "@fortawesome/free-solid-svg-icons";
import { Component } from "react";
import Action from "./Action";

class ProjectItem extends Component {
  render() {
    const { projectIdentifer, projectName, projectDescription } = this.props;

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
                  redirectTo="/"
                  actionIcon={faEdit}
                  displayText="Update Project"
                  customClasses="update text-success"
                />
                <Action
                  redirectTo="/"
                  actionIcon={faFlagCheckered}
                  displayText="Delete Project"
                  customClasses="delete text-danger"
                />
              </ul>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default ProjectItem;
