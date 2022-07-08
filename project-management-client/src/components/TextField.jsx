import { Component } from "react";
class TextField extends Component {
  state = {};
  render() {
    let {
      type,
      placeHolder,
      name,
      controlledBy,
      valueChangedHandler,
      isDisabled,
    } = this.props;

    controlledBy = controlledBy || "";

    return (
      <div className="mb-3">
        <input
          type={type}
          className="form-control form-control-lg "
          placeholder={placeHolder}
          name={name}
          value={controlledBy}
          onChange={valueChangedHandler}
          disabled={isDisabled && "disabled"}
        />
      </div>
    );
  }
}

export default TextField;
