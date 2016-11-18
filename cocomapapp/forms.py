from django import forms

from cocomapapp.models import User, Post


class RegisterForm(forms.Form):
    email = forms.EmailField(label='Your email', max_length=100, widget=forms.EmailInput(attrs={'placeholder': 'Email', 'class' : 'form-control'}))
    first_name = forms.CharField(label='Password', max_length=100, widget=forms.TextInput(attrs={'placeholder': 'Name', 'class' : 'form-control'}))
    last_name = forms.CharField(label='Password', max_length=100, widget=forms.TextInput(attrs={'placeholder': 'Surname', 'class' : 'form-control'}))
    password = forms.CharField(label='Password', max_length=100, widget=forms.PasswordInput(attrs={'placeholder': 'Password', 'class' : 'form-control'}))

    class Meta:
        model = User

class LoginForm(forms.Form):
    email = forms.EmailField(label='Your email', max_length=100, widget=forms.TextInput(attrs={'placeholder': 'Email', 'class' : 'form-control'}))
    password = forms.CharField(label='Password', max_length=100, widget=forms.PasswordInput(attrs={'placeholder': 'Password', 'class' : 'form-control'}))

    class Meta:
        model = User
# 
# class PostAdd(forms.Form):
#     content = forms.CharField(max_length=1000, widget=forms.TextInput(attrs={'placeholder': 'Write something', 'class' : 'input-group'})
#
#     class Meta:
#         model = Post
