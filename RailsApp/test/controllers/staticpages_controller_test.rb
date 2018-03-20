require 'test_helper'

class StaticpagesControllerTest < ActionDispatch::IntegrationTest
  def setup
    @base_title = "Ruby on Rails Tutorial Sample App"
  end
  test "should get hello" do
    get staticpages_hello_url
    assert_response :success
    assert_select "title", "Hello | #{@base_title}"
  end
  test "should get about" do
    get staticpages_about_url
    assert_response :success
    assert_select "title", "About | #{@base_title}"
  end
  test "should get default route" do
  get "/"
  assert_response :success
  assert_select "title", "Hello | #{@base_title}"
  end
end
