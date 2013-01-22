(ns divergence.fingerprint-test
  (:use clojure.test
        divergence.fingerprint))

(testing "find-server"
  (deftest finds-the-fingerprinted-server
    (is (= "https://example.com"
           (find-server "abcdef12345" { "abcdef12345" "https://example.com" })))))

(testing "extract-fingerprint"
  (deftest resolves-fingerpring-from-request
    (is (= "abcdefg123456"
           (extract-fingerprint {:headers { "Authorization" "token abcdefg123456" }}))))
  (deftest resolves-empty-string-without-authorization
    (is (= ""
           (extract-fingerprint {:headers {}})))))

(testing "expand"
  (def request {:headers { "Authorization" "token abcdefg123456" }})
  (def mapping { "abcdefg123456" "https://example.com" })

  (deftest expand-known-fingerprint
    (is (= "https://example.com" (expand request mapping))))

  (deftest does-not-expand-unknown-fingerprint)
    (is (empty? (expand {:headers {}} mapping))))
