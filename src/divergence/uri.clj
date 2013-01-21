(ns divergence.uri
  (:import (java.net URI)))

(defn path [uri] (.getPath (URI. uri)))
