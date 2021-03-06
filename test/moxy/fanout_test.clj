(ns moxy.fanout-test
  (:use clojure.test
        moxy.fanout))

(testing "expand"
  (def request {:uri "/api/missions"})
  (def servers ["https://acme.example.com" "https://example.com"])

  (deftest expand-all-servers
    (is (= "https://acme.example.com/api/missions"
           (first (expand request servers))))))
