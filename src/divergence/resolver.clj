(ns divergence.resolver
  (:require (divergence [static :as static]
                        [fingerprint :as finger]
                        [fanout :as fanout])))

(defn resolve-urls [request mappings]
  (let [static-expanded (static/expand request (get mappings :static))
        finger-expanded (finger/expand request (get mappings :finger))
        fanout-expanded (fanout/expand request (get mappings :fanout))
        expanders [static-expanded finger-expanded fanout-expanded]]
    (or (first (drop-while empty? expanders)) [])))
