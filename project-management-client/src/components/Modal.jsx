import { Component } from "react";
class Modal extends Component {
  state = {};
  render() {
    const { title, body, onClick } = this.props;

    return (
      <div class="modal" tabindex="-1">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">{title}</h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              <p>{body}</p>
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Cancel
              </button>
              <button type="button" class="btn btn-primary" onClick={onClick}>
                Okay
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Modal;
