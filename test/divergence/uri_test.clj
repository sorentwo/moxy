(ns divergence.uri-test
  (:use clojure.test
        divergence.uri))

(testing "path"
  (deftest extracts-path
    (is (= "/terms" (path "http://example.com/terms")))))
