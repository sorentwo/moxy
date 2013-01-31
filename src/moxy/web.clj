(ns moxy.web
  (:use me.shenfeng.http.server
        clojure.walk
        [moxy.resolver :as resolver]))

; we need an interface for getting/setting term
(def mappings {:static #{"http://localhost:3001/terms"}
               :finger  {}
               :fanout #{"http://localhost:3000"}})

(defn normalize-headers [response]
  (dissoc (stringify-keys (response :headers)) "transfer-encoding"))

(defn app [request]
  (let [expanded (resolver/expand  request mappings)
        response (resolver/realize request expanded)]
    {:status  (response :status)
     :headers (normalize-headers response)
     :body    (response :body)}))

(defn -main []
  (let [port (Integer/parseInt (System/getenv "PORT"))]
    (run-server app {:port port
                     :thread 4
                     :queue-size 20480})))
