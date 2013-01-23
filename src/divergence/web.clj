(ns divergence.web
  (:use me.shenfeng.http.server))

(defn app [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str request)})

(defn -main []
  (let [port (Integer/parseInt (System/getenv "PORT"))]
    (run-server app {:port port
                     :thread 4
                     :queue-size 20480})))
