import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Component } from "react";
class Action extends Component {
  render() {
    const { redirectTo, actionIcon, displayText, customClasses } = this.props;
    const listItemClass = `list-group-item ${customClasses}`;
    return (
      <a href={redirectTo} className="text-decoration-none">
        <li className={listItemClass}>
          <FontAwesomeIcon icon={actionIcon} /> {displayText}
        </li>
      </a>
    );
  }
}

export default Action;
