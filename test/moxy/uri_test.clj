(ns moxy.uri-test
  (:use clojure.test
        moxy.uri))

(testing "path"
  (deftest extracts-path
    (is (= "/terms" (path "http://example.com/terms")))))
