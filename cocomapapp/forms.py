from django import forms

from cocomapapp.models import User


class RegisterForm(forms.Form):
    username = forms.CharField(label='Your name', max_length=100, widget=forms.TextInput(attrs={'placeholder': 'Username', 'class' : 'form-control'}))
    password = forms.CharField(label='Password', max_length=100, widget=forms.PasswordInput(attrs={'placeholder': 'Password', 'class' : 'form-control'}))

    class Meta:
        model = User

class LoginForm(forms.Form):
    username = forms.CharField(label='Your name', max_length=100, widget=forms.TextInput(attrs={'placeholder': 'Username', 'class' : 'form-control'}))
    password = forms.CharField(label='Password', max_length=100, widget=forms.PasswordInput(attrs={'placeholder': 'Password', 'class' : 'form-control'}))

    class Meta:
        model = User
