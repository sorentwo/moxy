(ns divergence.fanout)

(defn expand [{uri :uri} servers]
  (map #(str % uri) servers))
