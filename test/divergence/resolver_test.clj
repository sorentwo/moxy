(ns divergence.resolver-test
  (:use clojure.test
        divergence.resolver))

(testing "expand"
  (deftest expand-static-url
    (let [request  {:uri "/terms"}
          mappings {:static ["http://example.com/terms"]}]
    (is (= '("http://example.com/terms") (expand request mappings)))))

  (deftest expand-fingerprinted-url
    (let [request {:uri     "/api/missions"
                   :headers { "Authorization" "token abcdefg123456" }}
          mapping {:static  []
                   :finger  { "abcdefg123456" "http://example.com" }}]
    (is (= '("http://example.com/api/missions") (expand request mapping)))))

  (deftest expand-fanout-url
    (let [request {:uri     "/api/missions" :headers {}}
          mapping {:static  []
                   :finger  {}
                   :fanout  ["http://acme.example.com" "http://corp.example.com"]}]
    (is (= '("http://acme.example.com/api/missions"
             "http://corp.example.com/api/missions") (expand request mapping)))))

  (deftest expand-no-urls
    (let [request {:uri "/api/missions" :headers {}}
          mapping {:static [] :finger {} :fanout []}]
      (is (= '() (expand request mapping))))))

(testing "realize")
