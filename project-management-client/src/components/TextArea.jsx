import { Component } from "react";
class TextArea extends Component {
  state = {};
  render() {
    const { placeHolder, name, controlledBy, valueChangedHandler } = this.props;
    return (
      <div className="mb-3">
        <textarea
          className="form-control form-control-lg"
          placeholder={placeHolder}
          name={name}
          value={controlledBy}
          onChange={valueChangedHandler}
        ></textarea>
      </div>
    );
  }
}

export default TextArea;
