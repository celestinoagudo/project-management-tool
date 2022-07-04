import { useNavigate, useParams } from "react-router-dom";

export function getComponentWithParamsAndNavigate(Component) {
  return (props) => (
    <Component {...props} params={useParams()} navigate={useNavigate()} />
  );
}
