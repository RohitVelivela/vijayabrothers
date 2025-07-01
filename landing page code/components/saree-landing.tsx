"use client";

import { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog';
import { LogIn, Eye, EyeOff } from 'lucide-react';

const SareeLanding = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [loginForm, setLoginForm] = useState({ username: '', password: '' });
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  console.log("SareeLanding component mounted");

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Login attempted with:", loginForm);
    // Add your login logic here
    setIsDialogOpen(false);
  };

  const handleInputChange = (field: string, value: string) => {
    console.log(`${field} changed to:`, value);
    setLoginForm(prev => ({
      ...prev,
      [field]: value
    }));
  };

  return (
    <div className="min-h-screen relative overflow-hidden">
      {/* Background Image with Overlay */}
      <div 
        className="absolute inset-0 bg-saree-hero bg-cover bg-center bg-no-repeat"
        style={{
          backgroundImage: 'url("https://assets.macaly-user-data.dev/swbgvr0sarltthc13dbckzhn/new-chat/o2fjm96njFCiPM2vFUtkh/landing-page.png")'
        }}
      />
      
      {/* Custom Overlay */}
      <div className="absolute inset-0 bg-saree-overlay" />
      
      {/* Logo */}
      <div className="absolute top-8 left-8 z-20">
        <div className="bg-white/95 backdrop-blur-sm rounded-xl p-4 shadow-xl border border-white/30">
          <img 
            src="https://assets.macaly-user-data.dev/swbgvr0sarltthc13dbckzhn/wr8hoqhldqgr0fz58ikkn7zj/otIbHxOGFPE2QUsVDHyaq/vb-logo-white-back.png"
            alt="Vijay Brothers Logo"
            className="h-16 w-auto object-contain"
          />
        </div>
      </div>

      {/* Main Content */}
      <div className="relative z-10 flex items-center justify-center min-h-screen px-4">
        <div className="text-center max-w-4xl mx-auto">
          
          {/* Greeting */}
          <div className="mb-8 animate-fadeInUp" style={{ animationDelay: '0.2s' }}>
            <h1 className="font-serif text-6xl md:text-8xl font-bold text-white mb-4 drop-shadow-2xl">
              Namaskaram
            </h1>
          </div>

          {/* Main Heading */}
          <div className="mb-6 animate-fadeInUp" style={{ animationDelay: '0.4s' }}>
            <h2 className="font-serif text-4xl md:text-6xl font-semibold text-gold-400 mb-4 drop-shadow-xl animate-shimmer">
              Ready to Curate Elegance?
            </h2>
          </div>

          {/* Subtitle */}
          <div className="mb-12 animate-fadeInUp" style={{ animationDelay: '0.6s' }}>
            <p className="font-sans text-xl md:text-2xl text-cream-100 max-w-3xl mx-auto leading-relaxed drop-shadow-lg">
              Experience the beauty of traditional craftsmanship and timeless elegance. 
              Each saree tells a story of heritage, artistry, and the magnificent culture of India.
            </p>
          </div>



          {/* Admin Login Button */}
          <div className="animate-fadeInUp animate-float" style={{ animationDelay: '1s' }}>
            <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
              <DialogTrigger asChild>
                <Button 
                  size="lg" 
                  className="bg-crimson-600 hover:bg-crimson-700 text-white font-sans font-semibold text-lg px-12 py-4 rounded-full shadow-2xl border-2 border-white/20 transition-all duration-300 hover:scale-105 hover:shadow-crimson-500/25"
                >
                  <LogIn className="mr-3 h-5 w-5" />
                  Proceed to Admin Login
                </Button>
              </DialogTrigger>
              
              <DialogContent className="sm:max-w-md bg-cream-50 border-gold-200">
                <DialogHeader>
                  <DialogTitle className="font-serif text-2xl text-charcoal-900 text-center">
                    Admin Access
                  </DialogTitle>
                </DialogHeader>
                
                <Card className="border-none shadow-none">
                  <CardHeader className="pb-4">
                    <CardTitle className="text-center font-sans text-charcoal-700">
                      Enter your credentials to continue
                    </CardTitle>
                  </CardHeader>
                  <CardContent>
                    <form onSubmit={handleLogin} className="space-y-4">
                      <div className="space-y-2">
                        <Label htmlFor="username" className="font-sans font-medium text-charcoal-800">
                          Username
                        </Label>
                        <Input
                          id="username"
                          type="text"
                          value={loginForm.username}
                          onChange={(e) => handleInputChange('username', e.target.value)}
                          className="border-gold-300 focus:border-gold-500 focus:ring-gold-500"
                          placeholder="Enter username"
                          required
                        />
                      </div>
                      
                      <div className="space-y-2">
                        <Label htmlFor="password" className="font-sans font-medium text-charcoal-800">
                          Password
                        </Label>
                        <div className="relative">
                          <Input
                            id="password"
                            type={showPassword ? "text" : "password"}
                            value={loginForm.password}
                            onChange={(e) => handleInputChange('password', e.target.value)}
                            className="border-gold-300 focus:border-gold-500 focus:ring-gold-500 pr-10"
                            placeholder="Enter password"
                            required
                          />
                          <Button
                            type="button"
                            variant="ghost"
                            size="sm"
                            className="absolute right-0 top-0 h-full px-3 py-2 hover:bg-transparent"
                            onClick={() => setShowPassword(!showPassword)}
                          >
                            {showPassword ? (
                              <EyeOff className="h-4 w-4 text-charcoal-600" />
                            ) : (
                              <Eye className="h-4 w-4 text-charcoal-600" />
                            )}
                          </Button>
                        </div>
                      </div>
                      
                      <Button 
                        type="submit" 
                        className="w-full bg-gold-gradient hover:opacity-90 text-white font-sans font-semibold transition-all duration-300"
                      >
                        Sign In
                      </Button>
                    </form>
                  </CardContent>
                </Card>
              </DialogContent>
            </Dialog>
          </div>

          {/* Decorative Elements */}
          <div className="absolute top-1/4 left-1/4 w-2 h-2 bg-gold-400 rounded-full animate-pulse opacity-60" />
          <div className="absolute top-1/3 right-1/3 w-3 h-3 bg-emerald-400 rounded-full animate-pulse opacity-40" style={{ animationDelay: '1s' }} />
          <div className="absolute bottom-1/4 left-1/3 w-2 h-2 bg-crimson-400 rounded-full animate-pulse opacity-50" style={{ animationDelay: '2s' }} />
        </div>
      </div>

      {/* Bottom Right Admin Section */}
      <div className="absolute bottom-8 right-8 z-20 text-right">
        <div className="mb-3">
          <p className="text-cream-100 font-serif text-lg font-semibold mb-1">
            Namaskaram
          </p>
          <p className="text-cream-200 font-sans text-sm">
            Ready to Curate Elegance?
          </p>
        </div>
        <button 
          onClick={() => {
            console.log("Admin login clicked");
            setIsLoginOpen(true);
          }}
          className="group bg-crimson-600 hover:bg-crimson-700 backdrop-blur-sm rounded-xl px-8 py-3 transition-all duration-300 hover:scale-105 border border-crimson-500 hover:border-gold-400 shadow-lg"
        >
          <div className="flex items-center space-x-2">
            <span className="text-white font-sans font-semibold">Proceed to Admin Login</span>
            <span className="text-white group-hover:translate-x-1 transition-transform">→</span>
          </div>
        </button>
      </div>
    </div>
  );
};

export default SareeLanding;