(ns divergence.fingerprint)

(defn find-server [fingerprint mapping]
  (get mapping fingerprint))

(defn extract-token [authorization]
  (nth (clojure.string/split authorization #"\s") 1 ""))

(defn extract-fingerprint [{headers :headers}]
  (extract-token (get headers "Authorization" "")))

(defn expand [request mapping]
  (let [server (find-server (extract-fingerprint request) mapping)]
    (str server (:uri request))))
