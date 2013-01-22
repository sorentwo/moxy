(ns divergence.fingerprint)

(defn find-server [fingerprint mapping]
  (get mapping fingerprint))

(defn extract-headers [request]
  (get request :headers))

(defn extract-authorization [headers]
  (get headers "Authorization" ""))

(defn extract-token [authorization]
  (nth (clojure.string/split authorization #"\s") 1 ""))

(defn extract-fingerprint [request]
  (-> request
    (extract-headers)
    (extract-authorization)
    (extract-token)))

(defn expand [request mapping]
  (find-server (extract-fingerprint request) mapping))
