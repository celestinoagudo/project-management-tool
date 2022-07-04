import { Component } from "react";
class TextField extends Component {
  state = {};
  render() {
    const { type, placeHolder, name, controlledBy, valueChangedHandler } =
      this.props;
    return (
      <div className="mb-3">
        <input
          type={type}
          className="form-control form-control-lg "
          placeholder={placeHolder}
          name={name}
          value={controlledBy}
          onChange={valueChangedHandler}
        />
      </div>
    );
  }
}

export default TextField;
