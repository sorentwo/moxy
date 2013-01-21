(ns divergence.static)

(defn known-route? [path url]
  (re-find (re-pattern path) url))

(defn expand-routes [path urls]
  (for [url urls :when (known-route? path url)] url))
