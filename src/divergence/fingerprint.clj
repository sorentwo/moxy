(ns divergence.fingerprint)

(defn find-server [fingerprint mapping]
  (get mapping fingerprint nil))

(defn extract-token [authorization]
  (nth (clojure.string/split authorization #"\s") 1 ""))

(defn extract-fingerprint [headers]
  (extract-token (get headers "Authorization" "")))

(defn expand [{uri :uri headers :headers} mapping]
  (let [server (find-server (extract-fingerprint headers) mapping)]
    (if server [(str server uri)] [])))
