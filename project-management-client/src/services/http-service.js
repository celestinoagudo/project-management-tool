import axios from "axios";

// axios.interceptors.response.use(null, (error) => {
//   const expectedError =
//     error.response &&
//     error.response.status >= 400 &&
//     error.response.status < 500;
//   if (!expectedError) {
//     logger.log(error);
//     // toast.error("An unexpected error occurred!");
//   }
//   return Promise.reject(error);
// });

export function setJwt(jwt) {
  // whenever you want to send an HTTP request,
  // make sure to include this header in the request
  axios.defaults.headers.common["x-auth-token"] = jwt;
}

const methods = {
  get: axios.get,
  post: axios.post,
  put: axios.put,
  delete: axios.delete,
  setJwt,
};

export default methods;
