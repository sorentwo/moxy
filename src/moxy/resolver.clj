(ns moxy.resolver
  (:require (moxy [static :as static]
                        [fingerprint :as finger]
                        [fanout :as fanout]))
  (:require [me.shenfeng.http.client :as http]))

(defn expand [request mappings]
  (let [static-expanded (static/expand request (mappings :static))
        finger-expanded (finger/expand request (mappings :finger))
        fanout-expanded (fanout/expand request (mappings :fanout))
        expanders [static-expanded finger-expanded fanout-expanded]]
    (or (first (drop-while empty? expanders)) [])))

(defn realize [request urls]
  (let [promises (map #(http/get % request) urls)
        failure? (fn [response] (= 404 (:status response)))]
    (first (drop-while failure? (map #(deref %) promises)))))
