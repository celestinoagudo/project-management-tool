import { Component } from "react";
class TextField extends Component {
  state = {};
  render() {
    const { type, placeHolder, name, valueChangedHandler, isDisabled } =
      this.props;
    let { controlledBy } = this.props;
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
