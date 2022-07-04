// import * as Sentry from "@sentry/react";
// import { Integrations } from "@sentry/tracing";

function init() {
  // Sentry.init({
  //   dsn: "https://6ab9661c86804bc9864d2f9b7e0148e6@o1033047.ingest.sentry.io/5999909",
  //   integrations: [new Integrations.BrowserTracing()],
  //   // We recommend adjusting this value in production, or using tracesSampler
  //   // for finer control
  //   tracesSampleRate: 1.0,
  //   environment: "development",
  // });
}

function log(error) {
  // Sentry.captureException(error);

  console.log(error);
}

export default { init, log };
