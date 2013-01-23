(ns divergence.static)

(defn known-route? [path url]
  (re-find (re-pattern path) url))

(defn expand [request urls]
  (let [uri (get request :uri)]
    (for [url urls :when (known-route? uri url)] url)))
