(ns divergence.fanout)

(defn expand [request servers]
  (let [uri (get request :uri)]
    (map #(str % uri) servers)))
