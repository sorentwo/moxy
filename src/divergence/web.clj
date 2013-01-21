(ns divergence.web
  (:use ring.adapter.jetty))

(defn app [req]
  {:status 200
   :headers {"Content/Type" "text/plain"}
   :body "divergence\n"})

(defn -main []
  (let [port (Integer/parseInt (System/getenv "PORT"))]
    (run-jetty app {:port port})))
