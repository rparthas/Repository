Rails.application.routes.draw do
  get 'staticpages/hello'
  get 'staticpages/about'

  root 'staticpages#hello'

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
