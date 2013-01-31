(ns moxy.uri
  (:import (java.net URI)))

(defn host   [uri] (.getHost (URI. uri)))
(defn path   [uri] (.getPath (URI. uri)))
(defn scheme [uri] (.getScheme (URI. uri)))
